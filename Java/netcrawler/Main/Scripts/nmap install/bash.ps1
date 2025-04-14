# Check if Nmap is already installed
$nmapPath = Get-Command nmap -ErrorAction SilentlyContinue

if ($nmapPath) {
    Write-Output "✅ Nmap is already installed at: $($nmapPath.Source)"
} else {
    Write-Output "⏳ Nmap not found. Installing via winget..."

    # Install Nmap using winget
    winget install -e --id Insecure.Nmap --accept-package-agreements --accept-source-agreements

    # Re-check installation
    $nmapPath = Get-Command nmap -ErrorAction SilentlyContinue
    if ($nmapPath) {
        Write-Output "✅ Nmap installed successfully at: $($nmapPath.Source)"
    } else {
        Write-Output "❌ Nmap installation may have failed. Please check manually."
    }
}
