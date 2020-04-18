<?php
	$res=array();
    if(isset($_POST['for_id']) && $_POST['for_pass']){
		
		$for_id=$_POST['for_id'];
        $for_pass=$_POST['for_pass'];
		
		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());		
		if(substr($for_id, 0, 1) == '1'){
		    $qry=mysqli_query($con,"UPDATE company_master SET comp_password = '$for_pass' where comp_id = '$for_id'");                        
			if($qry){			
                $res["success"] = 1;
                $res["message"] = "User(Company) Password Updated.";
            
                echo json_encode($res);                                
			} else{
				$res["success"] = 0;					
				$res["message"] = "Something went wrong..";
			
				echo json_encode($res);
			}	
        } else if(substr($for_id, 0, 1) == '3'){
		    $qry=mysqli_query($con,"UPDATE employee_master SET emp_password = '$for_pass' where emp_id = '$for_id'");                        
			if($qry){			
                $res["success"] = 1;
                $res["message"] = "User(Employee) Password Updated.";
            
                echo json_encode($res);                                
			} else{
				$res["success"] = 0;					
				$res["message"] = "Something went wrong..";
			
				echo json_encode($res);
			}	
        }
	}else{
		$res["success"] = 0;
		$res["message"] = "Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
?>