<?php
    $res=array();
    
    if(isset($_POST['id']) && ($_POST['name']) && ($_POST['gender']) && ($_POST['aadhar']) && ($_POST['phno1']) && ($_POST['email']) && ($_POST['add']) && ($_POST['city']) && ($_POST['pincode']) && ($_POST['state']) ){
		//&& ($_POST['dob'])
		$id=$_POST['id'];
        $name=$_POST['name'];
        $gen=$_POST['gender'];
        $dob=$_POST['dob'];
        $aadhar=$_POST['aadhar'];
        $phno1=$_POST['phno1'];
        $email=$_POST['email'];
        $add=$_POST['add'];
		$city=$_POST['city'];
		$pincode=$_POST['pincode'];
		$state=$_POST['state'];
        //?id=300002&name=Meet Patel&gender=M&dob=1999-12-12&aadhar=12345687912&phno1=132465870&email=meetv@patel.com&add=Gorwa&city=Vadodara&pincode=132465&state=Gujarat
        
		//?id=100008&name=Meet%20patel&email=sdfgh&city=dfgh&pincode=4567&phno1=34567&contact_person=sdfgh&lic_no=34567&get_no=34567&website=sdfghj

		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());		
        //UPDATE company_master SET comp_name = $name, comp_email = $email,comp_city = $city,comp_pincode = $pincode,comp_mob1 = $phno1,comp_person = $contact_person,comp_licno = $lic_no,comp_gstno = $get_no,comp_website = $website where comp_id=$id
        
		$qry=mysqli_query($con,"UPDATE employee_master SET emp_name = '$name', emp_gen = '$gen', emp_dob = '$dob', emp_aadharno = '$aadhar', emp_mob1 = '$phno1', emp_email = '$email', emp_address = '$add', emp_pincode = '$pincode', emp_city = '$city', emp_state = '$state' where emp_id='$id'");
		
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