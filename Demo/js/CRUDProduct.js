const linkAPI = "http://192.168.33.106:8080/"
function deleteProduct(id) {
  return fetch(linkAPI+'product/delete/' + id, {
    method: "DELETE",
  })
    .then(res => res.text())
    .then(res => console.log(res))
    .then(res => location.reload());

}

function updateProuct(id) {
  return fetch(linkAPI+'product/update/' + id, {
    method: "POST",
  })
    .then(res => res.text())
    .then(res => console.log(res))
    .then(res => location.reload());
}

// function orderById(category_id){
//   let { category_id } = useParams();
//   console.log(category_id)
//   return fetch('http://localhost:8080/product/get-by-category/' + category_id, {
//     method: "GET",
//     mode: "no-cors"
//     })
//     .then(res => res.text())
//     .then(res => console.log(res))
//     .then(res => location.reload());
// }