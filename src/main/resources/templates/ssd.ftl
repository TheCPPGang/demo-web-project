<html>

<head>
    <title>List of SSDs on Ebay</title> 
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<div>
	<label class="display-1">List of SSDs on Ebay</label>
	<table class="table table-striped table-dark">
		<thead>
			<tr>
			    <th scope="col">Title</td>
			    <th scope="col">Price</td> 
			    <th scope="col">Link</td> 
			</tr>
		</thead>
		<tbody>
			<#list ssds as ssd>
			    <tr>
					<td>${ssd.title}</td>
					<td>${ssd.price}</td>
					<td><a href="${ssd.link}">${ssd.link}</a></td>
			    </tr>
			</#list>
		</tbody>
	</table>
</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>