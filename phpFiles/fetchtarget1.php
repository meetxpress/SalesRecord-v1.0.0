<?php
    $res = array();
    if(isset($_POST['emp_id'])){

		$emp_id=$_POST['emp_id'];
		//$emp_id="300001";
        $con=mysqli_connect('localhost', 'root' ,'' ,'salesrecord') or die(mysqli_error());
        $qry1=mysqli_query($con,"select comp_id from employee_master where emp_id='$emp_id'");
		
		if(mysqli_num_rows($qry1) > 0 ){
			$row1 = mysqli_fetch_assoc($qry1);
		}
		//echo $row1['comp_id'];
		$comp_id=$row1['comp_id'];
	
		 $qry2=mysqli_query($con,"select atarget from emp_target where comp_id='$comp_id'");
        
        if(mysqli_num_rows($qry2) > 0 ){
			$row = mysqli_fetch_assoc($qry2);
		
			$res["success"] = 1;
			$res["message"] = "Data found Successfully.";
			
			$res["atarget"] = $row["atarget"];
			//$res["comp_email"] = $row["comp_email"];
			
			echo json_encode($res);
		}else{
            $res["success"] = 0;
            $res["message"]="Something went wrong.";
    
            echo json_encode($res);
        }
    }else{
        $res["success"]=0;
        $res["message"] = "Required Field(s) is(are) missing.";

        echo json_encode($res);
    }
?>