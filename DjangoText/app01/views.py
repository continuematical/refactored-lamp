from django.shortcuts import render, HttpResponse, redirect


# Create your views here.

def index(request):
    return HttpResponse("欢迎使用")


def login(request):
    if request.method == "GET":
        return render(request, "login.html")
    else:
        print(request.POST)
        username = request.POST.get("user")
        password = request.POST.get("pwd")
        if username == "admin" and password == "123":
            return redirect("https://www.chinaunicom.com/")
        else:
            return render(request, "login.html", {"error_msg": "登陆失败"})

        return HttpResponse("登陆成功")
