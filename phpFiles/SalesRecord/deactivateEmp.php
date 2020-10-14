<?php
    $res = array();
    if(isset($_POST['eid']) && ($_POST['status'])){

        $con=mysqli_connect('localhost', 'root' ,'' ,'salesrecord') or die(mysqli_error());
        if($_POST['status'] == "Active"){
            $qry=mysqli_query($con,"UPDATE employee_master SET emp_status = 'Deactive' where emp_id='".$_POST['eid']."'");
		}else{
            $qry=mysqli_query($con,"UPDATE employee_master SET emp_status = 'Active' where emp_id='".$_POST['eid']."'");
		}
        //$qry=mysqli_query($con,"UPDATE employee_master SET emp_status = '".$_POST['status']."' where emp_id='".$_POST['eid']."'");
        
        if($qry){
            $res["success"] = 1;
            $res["message"] = "Deactivated Successfully.";
    
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