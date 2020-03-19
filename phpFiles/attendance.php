<?php
    $res=array();
    if(isset($_POST['att1']) && ($_POST['att2'])){
        //($_POST['emp_id']) && ($_POST['comp_id']) && 

        //?emp_id=300001&att1=P&att2=P
        $con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
        if(($_POST['att1'] == 'P') && ($_POST['att2'] == 'A')){
            $qry=mysqli_query($con, "insert into attendance(emp_id, att1, att2) values ('300001', 'P', 'A')");
            if($qry){
                $res["success"] = 1;
                $res["message"] = "Punched1 Successfully.";
        
                echo json_encode($res);
            }else{
                $res["success"] = 0;
                $res["message"] = "Something went wrong.";
        
                echo json_encode($res);
            }

        } 
    
        if(($_POST['att1'] == 'P') && ($_POST['att2'] == 'P')){
            $qry=mysqli_query($con, "UPDATE attendance SET att1 = 'P', att2 = 'P' where emp_id = '".$_POST['emp_id']."'");
            if($qry){  
                $res["success"] = 2;
                $res["message"] = "Punched2 Successfully.";
        
                echo json_encode($res);
            } else{
                $res["success"] = 0;
                $res["message"] = "Something went wrong.";
        
                echo json_encode($res);
            }
        }
    }else{
        $res["success"] = 0;
        $res["message"] = "Required Fields are missing.";

        echo json_encode($res);
    }
?>