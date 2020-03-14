<?php
    $res = array();
    if(isset($_POST['cid'])){

        $con=mysqli_connect('localhost', 'root' ,'' ,'salesrecord') or die(mysqli_error());
        $qry=mysqli_query($con,"UPDATE company_master SET comp_status = 'Deactivate' where comp_id='".$_POST['cid']."'");
        
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