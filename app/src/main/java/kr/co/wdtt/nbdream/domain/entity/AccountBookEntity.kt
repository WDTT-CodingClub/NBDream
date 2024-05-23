package kr.co.wdtt.nbdream.domain.entity

data class AccountBookEntity(
    val userid: String? = null, //응답받을때 굳이 아이디를 받지 않아도 될 것 같아서 일단 null설정,
    val title: String,
    val category: String,
    val imageUrl: List<String> = emptyList(),
    val registerDate: String? = null, //요청
    val year: Int? = null, //응답 요청할때 날자를 분리하면 서버에서 날짜 테이블로 저장, 응답할때 나눠진값 반환
    val month: Int? = null, //응답 그런데 서버가 한명이니 상의해서 디바이스에서 계산할지 정해야할듯,
    val day: Int? = null, //응답 그런데 5월 데이터만 불러오려면 서버에선 날짜 테이블 나누긴 해야할듯?
    val dayName: String? = null, //응답
    val revenue: Long? = null,
    val expense: Long? = null,
    val totalRevenue: Long? = null,
    val totalExpense: Long? = null,
    val totalCost: Long? = null,
)
//계산은 서버에서