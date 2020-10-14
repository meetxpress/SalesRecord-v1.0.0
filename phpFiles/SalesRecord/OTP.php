<?php
    $res=array();
    
    $authKey = "YourAuthKey";
    $senderId = "102234";
    

    if(isset($_POST['phno'])){
        $mobileNumber = $_POST['phno'];

        $message = "Welcome to SalesRecord.";

        $postData = array(
            'authkey' => $authKey,
            'mobiles' => $mobileNumber,
            'message' => $message,
            'sender' => $senderId,
            'route' => $route
        );
        
        //API URL
        $url="http://api.msg91.com/api/sendhttp.php";

    }else{
        $res['success']=0;
        $res['message']="Required Fields are missing.";

        echo json_encode($res);
    }
?>
