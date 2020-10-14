<?php
	$response=array();
	if(isset($_GET['name']) && ($_GET['email']) && ($_GET['city']) && ($_GET['pincode']) && ($_GET['phno']) && ($_GET['contact_person']) && ($_GET['lic_no']) && ($_GET['gst_no']) && ($_GET['website']))
	{
		$cname=$_GET['name'];
		$cmail=$_GET['email'];
		$ccity=$_GET['city'];
		$cpin=$_GET['pincode'];
		$cphno=$_GET['phno'];
		$cconper=$_GET['contact_person'];
		$clic=$_GET['lic_no'];
		$cgst=$_GET['gst_no'];
		$cweb=$_GET['website'];
		
		//?name=SalesRecord pvt. ltd.&email=asdf@asdf.com&city=vadodara&pincode=936852&phno=132457980&contact_person=Mr. Abc QWE&lic_no=132456&gst_no=13246512&website=www.mksds.com
		
		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
		
		$response=mysqli_query($con,"insert into register_company(name, email, city, pincode, phno, contact_person, lic_no, gst_no, website) values ('$cname', '$cmail', '$ccity', '$cpin', '$cphno', '$cconper', '$clic', '$cgst', '$cweb')");
		if($response){
			$response["success"]=1;
			$response["message"]="Company Registered Successfully.";			
			
			echo json_encode($response);
		}else{
			$response["success"]=0;
			$response["message"]="Something went Wrong!";		
			
			echo json_encode($response);
		}
	}else{
		$response["success"]=0;
		$response["message"]="Required field(s) is missing.";		
		echo json_encode($response);	
	}
?>