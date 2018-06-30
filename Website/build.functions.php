<?php

function store_modify_file(){
    global $db;
    $target_dir = "buildings/".$_SESSION["loginid"]."/";
    $target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
    $uploadOk = 1;
    $fileType = pathinfo($target_file,PATHINFO_EXTENSION);
// Check file size
    if ($_FILES["fileToUpload"]["size"] > 10000000) {
        echo "Sorry, your file is too large.";
        $uploadOk = 0;
    }
// Allow certain file formats
    if($fileType != "tiff") {
        echo "Sorry, only TIFF files are allowed.";
        $uploadOk = 0;
    }
// Check if $uploadOk is set to 0 by an error
    if ($uploadOk == 0) {
        echo "Sorry, your file was not uploaded.";
// if everything is ok, try to upload file
    } else {
	    if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
                echo "The file " . basename($_FILES["fileToUpload"]["name"]) . " has been uploaded.";
                $query = $db->prepare("update building set version = ? , name = ? WHERE id = ?");
                $query->execute(array($_POST["version"],$_POST["name"],$_POST['id']));
            } else {
                echo "Sorry, there was an error uploading your file.";
            }
    }


}

function add_building_file(){
    global $db;
    $uploadOk = 1;
    $fileType = pathinfo(basename($_FILES["fileToUpload"]["name"]),PATHINFO_EXTENSION);
// Check file size
    if ($_FILES["fileToUpload"]["size"] > 10000000) {
        echo "Sorry, your file is too large.";
        $uploadOk = 0;
    }
// Allow certain file formats
    if($fileType != "tiff") {
        echo "Sorry, only TIFF files are allowed.";
        $uploadOk = 0;
    }
// Check if $uploadOk is set to 0 by an error
    if ($uploadOk == 0) {
        echo "Sorry, your file was not uploaded.";
// if everything is ok, try to upload file
    } else {
        $query = $db->prepare("insert into building (version,name,owner) value (?,?,?)");
        $query->execute(array(1,$_POST["name"],$_SESSION["username"]));
        $id = $db->lastInsertId();
        $target_dir = "buildings/".$_SESSION["loginid"]."/";
        $target_file = $target_dir . basename($_POST["name"],".tiff") . ".tiff";
        mkdir($target_dir );
        if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
            shell_exec("python3 testPact.py -f ".escapeshellarg($target_file)." -o ".escapeshellarg($target_dir . basename($target_file, ".tiff")).".graph");    
	    shell_exec("convert ".escapeshellarg($target_dir . basename($target_file, ".tiff")).".tiff ".escapeshellarg($target_dir . basename($target_file, ".tiff")).".png");
	    echo "The file " . basename($_FILES["fileToUpload"]["name"]) . " has been uploaded.";
        } else {
            echo "Sorry, there was an error uploading your file.";
        }
    }

}
