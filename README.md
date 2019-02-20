# Spring Boot ElasticSearch

1. Download [ElasticSearch](https://www.elastic.co/downloads/past-releases/elasticsearch-5-6-3) and run:
```
➜ ./elasticsearch-5.6.3/bin/elasticsearch
```

2. Download [kibana](https://www.elastic.co/downloads/past-releases/kibana-5-6-3) and run:
```
➜ ./kibana-5.6.3-linux-x86_64/bin/kibana
```

3. Open in the browser: `http://localhost:5601`

4. Select `DevTools` from `Kibana`.

5. Create Index `products`:
```
PUT /products?pretty
```

6. Add documents:
```
POST /products/default
{
  "id": "S1",
  "name": "Store1",
  "price": 23.6,
  "store_id": 1
}
```

7. Run project.