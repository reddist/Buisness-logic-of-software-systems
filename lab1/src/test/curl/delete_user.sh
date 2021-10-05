curl -X DELETE "http://localhost:15600/user/delete" \
  -H  "accept: application/json" \
  -H  "Content-Type: application/json" \
  -H  "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhcnZhbG9uQG1haWwucnUiLCJyb2xlcyI6WyJVU0VSIl0sImlhdCI6MTYzMzQ0MDk5NSwiZXhwIjoxNjM0MDQ1Nzk1fQ.gxpW1SRiqXHL6hsZGstLrCU_NW_tkM1CPsNLNvOoE6tQYZZjRiWl5Yl03YFYWCO21s9XLqAe0rfGJ_prd-y61g" \
  -d "{\"email\":\"arvalon@mail.ru\"}"