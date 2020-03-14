<?php
	$res=array();
	if(isset($_POST['id']) && ($_POST['name']) && ($_POST['email']) && ($_POST['city']) && ($_POST['pincode']) && ($_POST['phno1']) && ($_POST['contact_person']) && ($_POST['lic_no']) && ($_POST['get_no']) && ($_POST['website'])){
		
		$id=$_POST['id'];
		$name=$_POST['name'];
		$email=$_POST['email'];
		$city=$_POST['city'];
		$pincode=$_POST['pincode'];
		$phno1=$_POST['phno1'];
		$contact_person=$_POST['contact_person'];
		$lic_no=$_POST['lic_no'];
		$get_no=$_POST['get_no'];
		$website=$_POST['website'];
		
		//?id=100008&name=Meet%20patel&email=sdfgh&city=dfgh&pincode=4567&phno1=34567&contact_person=sdfgh&lic_no=34567&get_no=34567&website=sdfghj

		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());		
		//UPDATE company_master SET comp_name = $name, comp_email = $email,comp_city = $city,comp_pincode = $pincode,comp_mob1 = $phno1,comp_person = $contact_person,comp_licno = $lic_no,comp_gstno = $get_no,comp_website = $website where comp_id=$id

		$qry=mysqli_query($con,"UPDATE company_master SET comp_name = '$name', comp_email = '$email',comp_city = '$city',comp_pincode = '$pincode',comp_mob1 = '$phno1',comp_person = '$contact_person',comp_licno = '$lic_no',comp_gstno = '$get_no',comp_website = '$website' where comp_id='$id'");
		
		if($qry){
			$res["success"]=1;
			$res["message"]="Data updated Successfully.";
			
			echo json_encode($res);
		}else{
			$res["success"]=0;					
			$res["message"]="Error";
					 
			echo json_encode($res);
		}		
	}else{
		$res["success"]=0;
		$res["message"]="Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
?>