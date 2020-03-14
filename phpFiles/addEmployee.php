<?php
	$res=array();
    if(isset($_POST['eName']) && ($_POST['ePass']) && ($_POST['eGen']) && ($_POST['eDob']) && ($_POST['eAadhar']) && ($_POST['ePhno1']) && ($_POST['eMail']) && ($_POST['eAdd']) && ($_POST['ePincode']) && ($_POST['eCity']) && ($_POST['eState']) && ($_POST['eStatus'])){ 
		// 
        //?eName=Meet&ePass=abc123&eGen=M&&eAadhar=123456789012&ePhno1=1234567890&eMail=meet@patel.com&eAdd=Panchvati&ePincode=123456&eCity=Vadodara&eState=Gujarat&eStatus=active
	
		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());		
		$qry=mysqli_query($con,"insert into employee_master(comp_id, emp_id, emp_password, emp_name, emp_gen, emp_dob, emp_aadharno, emp_mob1, emp_mob2, emp_email, emp_address, emp_pincode, emp_city, emp_state, emp_status) values('100041' , '','".$_POST['ePass']."', '".($_POST['eName'])."', '".$_POST['eGen']."', '".$_POST['eDob']."', ".$_POST['eAadhar'].", '".$_POST['ePhno1']."','', '".$_POST['eMail']."', '".$_POST['eAdd']."', ".$_POST['ePincode'].", '".$_POST['eCity']."','".$_POST['eState']."', '".$_POST['eStatus']."')");


		if($qry){
			$res["success"]=1;
			$res["message"]="Data Inserted Successfully.";
			
			echo json_encode($res);
		}else{
			$res["success"]=0;					
			$res["message"]="Can't Insert Data.";
				
			echo json_encode($res);
		}		
	}else{
		$res["success"]=0;
		$res["message"]="Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
