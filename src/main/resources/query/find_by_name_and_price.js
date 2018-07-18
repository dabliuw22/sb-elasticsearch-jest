{
  "from": %s,
  "size": %s, 
  "query": {
    "bool": {
      "must": [
        {
          "match": {
            "name": {
              "query": "%s",
              "operator": "and"
            } 
          }
        }
      ],
      "filter": {
        "range": {
          "price": {
            "lte": %s
          }
        }
      }
    }
  },
  "_source": {
    "include": ["id", "name", "price", "store_id"]
  }
}