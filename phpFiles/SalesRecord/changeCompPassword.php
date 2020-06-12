<?php
    $res=array();
    
    //comp_id=100041&cOldPass=abc123&cNewPass=abcd1234
    if(isset($_POST['comp_id']) && ($_POST['cOldPass']) && ($_POST['cNewPass'])) {
		$con=mysqli_connect('localhost', 'root', '', 'salesrecord') or die(mysqli_error());		
        $qry1=mysqli_query($con, "select comp_password from company_master where comp_id='".$_POST['comp_id']."'");

		if($qry1){
            $n = mysqli_fetch_assoc($qry1);

            if($n["comp_password"] == $_POST['cOldPass']){
                $qry=mysqli_query($con, "UPDATE company_master SET comp_password='".$_POST['cNewPass']."' where comp_id='".$_POST['comp_id']."'");
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