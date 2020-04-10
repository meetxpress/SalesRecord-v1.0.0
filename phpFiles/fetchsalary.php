<?php
    $res = array();
    if(isset($_POST['emp_id'])){

		$emp_id=$_POST['emp_id'];
		//$emp_id="300001";
        $con=mysqli_connect('localhost', 'root' ,'' ,'salesrecord') or die(mysqli_error());
        $qry=mysqli_query($con,"select esal from employee_master where emp_id='$emp_id'");
        
        if(mysqli_num_rows($qry) > 0 ){
			$row = mysqli_fetch_assoc($qry);
			
			$res["success"] = 1;
			$res["message"] = "Data found Successfully.";
			
			$res["esal"] = $row["esal"];
			
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