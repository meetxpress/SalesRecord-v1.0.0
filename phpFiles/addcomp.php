<?php
	$res=array();
	if(isset($_POST['name']) && ($_POST['pass']) && ($_POST['email']) && ($_POST['city']) && ($_POST['pincode']) && ($_POST['phno1']) && ($_POST['contact_person']) && ($_POST['lic_no']) && ($_POST['status'])){
		// && ($_POST['comp_password']) 
		/*
		?name=ABC MCB SDK pvt. Ltd&pass=abc123&email=abc@bhf.com&city=Vadodara&pincode=123456&phno1=1231231239&phno2=1234567890&contact_person=Meet Patel&lic_no=12JH15G12GHIG2679UIWS&status=Active
		*/

		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
		//$qry=mysqli_query($con,"select * from super_admin where username='".$user."'");
			
		$qry=mysqli_query($con,"insert into company_master(comp_name, comp_password, comp_email, comp_city, comp_pincode, comp_mob1, comp_person, comp_licno, comp_gstno, comp_website, comp_status) values ('".$_POST['name']."','".($_POST['pass'])."','".$_POST['email']."', '".$_POST['city']."', ".$_POST['pincode'].", '".$_POST['phno1']."', '".$_POST['contact_person']."', '".$_POST['lic_no']."', '12345678', 'asbc.com', '".$_POST['status']."')");

		//$res["abc"]="insert into company_master ( comp_name, comp_password, comp_email, comp_city, comp_pincode, comp_mob1, comp_mob2, comp_person, comp_licno, comp_gstno, comp_website) values ('".$_POST['name']."','xdgxgd ','".$_POST['email']."','".$_POST['city']."', ".$_POST['pincode'].",'".$_POST['phno1']."','237462873','".$_POST['contact_person']."', '".$_POST['lic_no']."','12345678','asbc.com')";
		if($qry){
			$res["success"]=1;
			$res["message"]="Company Regsiterd Successfully.";
			
			echo json_encode($res);
		}else{
			$res["success"]=0;					
			$res["message"]="Can't Register Company.";
				
			echo json_encode($res);
		}		
	}else{
		$res["success"]=0;
		$res["message"]="Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
