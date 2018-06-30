<?php
require_once "header.php";
require_once "build.functions.php";
if (isset($_FILES['fileToUpload']) && isset($_POST['name'])){
    if (isset($_POST['id']) && isset($_POST['version']) ){
        store_modify_file();
    }
    elseif (!(isset($_POST['id']) || isset($_POST['version']))){
        add_building_file();
    }
}


elseif (isset($_GET['action'])){
    if ($_GET['action'] == 'add'){
        show_add_form();
    }
    elseif ($_GET['action'] == 'modify'){
        if (isset($_GET['id'])){
            $id = intval($_GET["id"]);
            if ($id > 0){
                show_modify_form($id);
            }
            else{
                header('Location: index.php');
            }
        }
        else{
            header('Location: index.php');
        }
    }
    else{
        header('Location: index.php');
    }
}
else{
    header('Location: index.php');
}



require_once "footer.php";