package com.example.geekhub
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.geekhub.data.LoginRequest
import com.example.geekhub.data.LoginResponse
import com.example.geekhub.databinding.ActivityLoginBinding
import com.example.geekhub.retrofit.NetWorkInterface
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    lateinit var pref : SharedPreferences
    lateinit var saveId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = getSharedPreferences("idKey", 0)
        saveId =pref.getString("id", "").toString()
        Log.d("태그", saveId)
        println(saveId)

        if (saveId != "") {
            val intent = Intent(applicationContext, ReadyActivity::class.java)
            startActivity(intent)
            finish()

        }




        sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        binding.loginButton.setOnClickListener{
            var id = binding.editId.text.toString()
            var pw = binding.editPw.text.toString()
            println(id)
            println(pw)

            val retrofit = Retrofit.Builder().baseUrl("http://k7c205.p.ssafy.io:9002/")
                .addConverterFactory(
                GsonConverterFactory.create()).build()

            val callData = retrofit.create(NetWorkInterface::class.java)
            val body = LoginRequest()
            body.userId = id
            body.password = pw
            val call = callData.login(body)
            call.enqueue(object:Callback<LoginResponse>{
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext,"로그인실패",Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    println("체크")
                    println(response.code())

                    if(response.isSuccessful){
                        val data = response.body()?.key
                        Log.d("키값입니다",data.toString())
                        saveDate(data.toString())

                        Toast.makeText(applicationContext,"로그인성공",Toast.LENGTH_SHORT).show()
                        intent = Intent(this@LoginActivity, StartActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                        startActivity(intent)
                        finish()


                    }else{
                        Toast.makeText(applicationContext,"로그인실패",Toast.LENGTH_SHORT).show()
                        try {
                            println(response.code())
                            println("체크")
                            println(response.errorBody().toString())
                            println(response.errorBody()?.string()!!)


                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            })
        }

    }

    fun saveDate( id :String ){
        pref =getSharedPreferences("idKey", MODE_PRIVATE) //shared key 설정
        var edit = pref.edit() // 수정모드
        edit.putString("id", id ) // 값 넣기
        edit.apply() // 적용하기
    }
}