function searchProduct(name){
    return fetch('http://localhost:8080/product/search-product?name=' + name, {
        method: 'GET',
      })
      .then(res => res.text())
      .then(res => console.log(res))
      .then(res => location.reload());
  }