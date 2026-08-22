<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <title>Edit Product</title>
  <link rel="stylesheet" href="../assets/css/style.css">
</head>

<body>

<jsp:include page="../components/navbar.jsp" />

<div class="container" style="max-width:480px">

  <h2>Edit Product</h2>
  
   <form action="<%= request.getContextPath() %>/admin/UpdateAdminProduct"
          method="post"
          enctype="multipart/form-data">

        <input type="hidden" name="id" value="<%= request.getAttribute("id") %>">

        <input class="input-box" name="name"
               value="<%= request.getAttribute("name") %>" required>

        <input class="input-box" name="price"
               value="<%= request.getAttribute("price") %>" required>
               
        <input class="input-box" name="category"
           value="<%= request.getAttribute("category") %>" required>
           
        <textarea class="input-box" name="description" required>
    		<%= request.getAttribute("description") %>
        </textarea>

        <p>Current Image:</p>
        <img src="<%= request.getContextPath() %>/<%= request.getAttribute("imageUrl") %>"
             style="width:120px;border-radius:8px">


        <input class="input-box" type="file" name="image">

        <button class="btn">Update Product</button>

    </form>

</div>

</body>
</html>
