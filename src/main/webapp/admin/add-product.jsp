<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <title>Add Product</title>
  <link rel="stylesheet" href="../assets/css/style.css">
</head>

<body>
<jsp:include page="../components/navbar.jsp" />

<div class="container" style="max-width:480px">

  <h2>Add Product</h2>

  <form action="<%= request.getContextPath() %>/admin/AddAdminProduct"
      method="post"
      enctype="multipart/form-data">

    <input class="input-box" name="name" placeholder="Game Name" required>

    <input class="input-box" name="price" placeholder="Price" required>
    
    <input class="input-box" name="category" placeholder="Category" required>
    
    <input class="input-box" name="description" placeholder="Brief description of game" required>

    <input class="input-box" type="file" name="image" accept="image/*" required>

    <button class="btn">Add Product</button>
</form>
  

</div>

</body>
</html>
