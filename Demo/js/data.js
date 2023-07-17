
fetch("http://localhost:8080/product/get-all").then((data) => {
    
return data.json();
}).then((objdata) => {
    console.log(objdata);
    let tabledata = "";
    objdata.map((values) => {
        tabledata += `<div><tr>     
                   <th>${values.id}</th>
                 <th>${values.name}</th>
                    <th>${values.price}</th>
                     <th><img id='image' src="${values.avatar}" /></th>
                    <th>${values.description}</th>
                    <th>${values.categoryid}</th>
                    <th>
                     <a href="productEdit.html?id=${values.id}" type="button" class=" btn btn-success" >Sửa</a>
                         <a type="button" onclick='deleteProduct(${values.id})'  class=" btn btn-danger">Xóa</a>
                    </th></tr></div>`;
                    

    });
document.getElementById("tblBody").innerHTML = tabledata;
    
}).catch((err) => { console.log(err) });
