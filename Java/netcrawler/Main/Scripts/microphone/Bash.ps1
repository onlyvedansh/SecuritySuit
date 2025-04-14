Write-Output "Checking Camera and Microphone usage..."

# Check for Camera (usually accessed via device name containing "video")
$cameraProcesses = Get-Process | Where-Object {
    $_.Modules | Where-Object { $_.ModuleName -like "*mf.dll" -or $_.ModuleName -like "*ksuser.dll" -or $_.ModuleName -like "*avicap32.dll" }
} 2>$null

# Check for Microphone (audio APIs usually include these)
$micProcesses = Get-Process | Where-Object {
    $_.Modules | Where-Object { $_.ModuleName -like "*wdmaud.drv" -or $_.ModuleName -like "*mmdevapi.dll" }
} 2>$null

if ($cameraProcesses.Count -eq 0) {
    Write-Output "✅ Camera is safe (not in use)"
} else {
    foreach ($proc in $cameraProcesses) {
        Write-Output " Warning: Camera is being used by $($proc.ProcessName)"
    }
}

if ($micProcesses.Count -eq 0) {
    Write-Output "✅ Microphone is safe (not in use)"
} else {
    foreach ($proc in $micProcesses) {
        Write-Output " Warning: Microphone is being used by $($proc.ProcessName)"
    }
}
