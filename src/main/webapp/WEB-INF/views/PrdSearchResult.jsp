<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8">
            <title>상품검색 결과</title>
            <style type="text/css">
                .prdTitle {
                    display: inline-block;
                    width: 300px;
                    white-space: nowrap;
                    overflow: hidden;
                    margin: 0px;
                }

                table {
                    border-collapse: collapse;
                }

                td,
                th {
                    border-bottom: 1px solid black;
                    padding: 3px 7px;
                }

                .prdArea {
                    margin-top: 15px;
                    border: 2px solid black;
                    width: 600px;
                    height: 700px;
                    overflow: scroll;
                    overflow-x: hidden;
                    padding: 3px;
                }

                select {
                    float: right;
                    border: 2px solid;
                    border-radius: 10px;
                }
            </style>
        </head>

        <body>
            <h1>PrdSearchResult.jsp</h1>
            <div style="display:flex;">
                <div class="prdArea">
                    <table>
                        <thead>
                            <tr>
                                <th>쇼핑몰</th>
                                <th>상품이름</th>
                                <th><select onchange="SortOption(this.value)">
                                    <option value="price_desc">가격 높은순</option>
                                    <option value="price_asc">가격 낮은순</option>
                                </select></th>
                            </tr>
                        </thead>
                        <tbody class="Coopang">
                            <c:forEach items="${prdList_coopang}" var="prdInfo">
                                <tr>
                                    <td>${prdInfo.prdSite}</td>
                                    <td><a class="prdTitle" title="${prdInfo.prdName}"
                                            href="${prdInfo.prdUrl}">${prdInfo.prdName}</a></td>
                                    <td>${prdInfo.prdPrice}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="prdArea">
                    
                    <table>
                        <thead>
                            <tr>
                                <th>쇼핑몰</th>
                                <th>상품이름</th>
                                <th><select onchange="SortOption(this.value)">
                                    <option value="price_desc">가격 높은순</option>
                                    <option value="price_asc">가격 낮은순</option>
                                </select></th>
                            </tr>
                        </thead>
                        <tbody class="Gmarket">
                            <c:forEach items="${prdList_gmarket}" var="GprdInfo">
                                <tr>
                                    <td>${GprdInfo.prdSite}</td>
                                    <td><a class="prdTitle" title="${GprdInfo.GprdName}"
                                            href="${GprdInfo.GprdUrl}">${GprdInfo.GprdName}</a></td>
                                    <td class="priceTag">${GprdInfo.GprdPrice}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="prdArea">
                    <table>
                        <thead>
                            <tr>
                                <th>쇼핑몰</th>
                                <th>상품이름</th>
                                <th><select onchange="SortOption(this.value)">
                                    <option value="price_desc">가격 높은순</option>
                                    <option value="price_asc">가격 낮은순</option>
                                </select></th>
                            </tr>
                        </thead>
                        <tbody class="11st">
                            <c:forEach items="${prdList_11st}" var="prd11">
                                <tr>
                                    <td>${prd11.prdSite}</td>
                                    <td><a class="prdTitle" title="${prd11.prdName}"
                                            href="${prd11.prdUrl}">${prd11.prdName}</a></td>
                                    <td class="priceTag">${prd11.prdPrice}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </body>
        <script>
            function SortOption(sortPrice) {
                //let priceList = sortPrice.parentElement.parentElement.parentElement.nextElementSibling;
                let priceList = document.querySelectorAll('tbody.Gmarket>tr');
                console.log(priceList);
                let priceList_arr = Array.from(priceList);

                let SortArray = [];
                SortArray[0] = priceList_arr.shift();
                console.log(SortArray);
                for (let price_arr of priceList_arr) {
                    let SortPrice_Arr = Number(price_arr.querySelector('.priceTag').innerText);

                    idx = -1;
                    for (let SortIdx in SortArray) {
                        let PriceSortIndex = Number(SortArray[SortIdx].querySelector('.priceTag').innerText);
                  //      console.log(SortPrice_Arr + "::" + PriceSortIndex);

                        let sortCheck = false;
                        switch (sortPrice) {
                            case "price_desc":
                                sortCheck = SortPrice_Arr > PriceSortIndex
                                break;
                            case "price_asc":
                                sortCheck = SortPrice_Arr < PriceSortIndex
                                break;
                        }
                        if (sortCheck) {
                            idx = SortIdx
                            break;
                        }
                    }
                    if (idx > -1) {
                        SortArray.splice(idx, 0, price_arr);
                    } else {
                        SortArray.push(price_arr);
                    }
                }
                let SortArea = document.querySelector('tbody.Gmarket');
                SortArea.innerHTML="";
                for(let item of SortArray){
                    SortArea.appendChild(item);
                }
            }

        </script>

        </html>