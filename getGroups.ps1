param (
     $OutFile = (Get-Date -Format yyyy-MM-dd) + "_GALEntries.csv"
)
$Outlook = New-Object -ComObject Outlook.Application
$GlobalAddressList = $Outlook.Session.GetGlobalAddressList().AddressEntries
$TotalObjects = $GlobalAddressList.Count
$i = 1
$RecordData = @{}
foreach ($entry in $GlobalAddressList)
{
	Write-Progress -Activity "Exporting Global Address List Entries" -PercentComplete (($i / $TotalObjects) * 100) -Status "$($TotalObjects - $i) entries remaining"
	If ($entry.Address -match "\/o\=")
	{
		if($entry.GetExchangeDistributionList()){
			$RecordData[$entry.Name] = @{}
			foreach($member in $entry.GetExchangeDistributionList().members){
				if($member.getExchangeUser()){
					$RecordData[$entry.Name][$member.GetExchangeUser().alias] = $member.GetExchangeUser().alias
				}
			}

			#$Record = New-Object PSobject -Property $RecordData
			##$Record | Export-csv $OutFile -NoTypeInformation -Append
		}
	}
	$i++
}
ConvertTo-Json $RecordData | Out-File -FilePath $OutFile