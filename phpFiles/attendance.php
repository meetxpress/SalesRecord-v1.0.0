<?php
    $res=array();
    if(isset($_POST['emp_id']) && ($_POST['pi_date']) && ($_POST['pi_time']) && ($_POST['pi_locLat'])  && ($_POST['pi_locLong']) && ($_POST['po_time']) {
        //&& ($_POST['po_locLat']) && ($_POST['po_locLong']))
        /*
            emp_id
            pi_date
            pi_time
            pi_locLat
            pi_locLong
            po_time
            po_locLat
            po_locLong
        */
        //37.421998333
        //-122.0840000
        //?emp_id=300001&pi_date=14-3-2020&pi_time=10:12&pi_locLat=37.421998333&pi_locLong=-122.0840000&po_time=11:55&po_locLat=37.421998333&po_locLong=-122.0840000
        $con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());
        $qry=mysqli_query($con, "insert into emp_attendance (emp_id, pi_date, pi_time, pi_locLat, pi_locLong) values ('".$_POST['emp_id']."', '".$_POST['pi_date']."', '".$_POST['pi_time']."', '".$_POST['pi_locLat']."', '".$_POST['pi_locLong']."')");
        //, '".$_POST['po_time']."', '".$_POST['po_locLat']."', '".$_POST['po_locLong']."')");
        //po_time, po_locLat, po_locLong
        if($qry){
            $res["success"] = 1;
            $res["message"] = "Punched Successfully.";
    
            echo json_encode($res);
        }else{
            $res["success"] = 0;
            $res["message"] = "Something went wrong.";
    
            echo json_encode($res);
        }
    }else{
        $res["success"] = 0;
        $res["message"] = "Required Fields are missing.";

        echo json_encode($res);
    }
?>