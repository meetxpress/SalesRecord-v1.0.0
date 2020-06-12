<?php
    $res=array();
    
    //?emp_id=300001&eOldPass=abc123&eNewPass=abcd1234
    if(isset($_POST['emp_id']) && ($_POST['eOldPass']) && ($_POST['eNewPass'])) {
		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());		
        $qry1=mysqli_query($con, "select emp_password from employee_master where emp_id='".$_POST['emp_id']."'");

		if($qry1){
            $n = mysqli_fetch_assoc($qry1);

            if($n["emp_password"] == $_POST['eOldPass']){
                $qry=mysqli_query($con, "UPDATE employee_master SET emp_password='".$_POST['eNewPass']."' where emp_id='".$_POST['emp_id']."'");
                if($qry){
                    $res["success"]=1;
                    $res["message"]="Password Changed Successfully.";
                    
                    echo json_encode($res);                      
                }   
            }else{
                $res["success"]=0;					
                $res["message"]="Can't Change Password.";
                    
                echo json_encode($res);
            }
		}else{
            $res["success"]=0;					
            $res["message"]="Invalid User.";
                
            echo json_encode($res);
        }		
	}else{
		$res["success"]=0;
		$res["message"]="Required Field(s) is(are) missing.";
		
		echo json_encode($res);
	}
?>