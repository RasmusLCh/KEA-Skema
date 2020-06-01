param (
     $OutFile = (Get-Date -Format yyyy-MM-dd) + "_GALEntries.csv"
)
$Outlook = New-Object -ComObject Outlook.Application
$GlobalAddressList = $Outlook.Session.GetGlobalAddressList().AddressEntries
$TotalObjects = $GlobalAddressList.Count
$i = 1
foreach ($entry in $GlobalAddressList)
{
	Write-Progress -Activity "Exporting Global Address List Entries" -PercentComplete (($i / $TotalObjects) * 100) -Status "$($TotalObjects - $i) entries remaining"
	If ($entry.Address -match "\/o\=")
	{
		if($entry.getExchangeUser()){
			$RecordData = [ordered]@{
				Name   = $entry.Name
				# PrimarySmtpAddress = $entry.GetExchangeUser().PrimarySmtpAddress
				# x500   = $entry.Address
				alias = $entry.GetExchangeUser().alias
			}
			$Record = New-Object PSobject -Property $RecordData
			$Record | Export-csv $OutFile -NoTypeInformation -Append		
		}
	}
	$i++
}