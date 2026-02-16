пример запроса с пагинацией
```
query Photos($page:Int!, $size:Int!) {
photos(page: $page, size: $size) {
edges {
node {
id,
description,
lastModifyDate
}
}
pageInfo {
hasPreviousPage
hasNextPage
}
}
}
```