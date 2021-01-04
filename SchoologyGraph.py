import requests
from bs4 import BeautifulSoup
import multiprocessing
exclusion_prefixes=["IS:","HS ","I ","Teaching","Quizzes","Facebook","Supervised","SAS Catalyst","The Film","Music Faculty","TEST","Compass","Science Department","Class"]
cookies={
    "domain": ".sas.schoology.com",
    "name": "",
    "path": "/",
    "value": ""
}
jar = requests.cookies.RequestsCookieJar()
jar.set(cookies['name'],cookies['value'],domain=cookies['domain'],path=cookies['path'])
page_count=1
schoology_graph={}
data=requests.get("https://sas.schoology.com/enrollments/edit/members/course/372447719/ajax?p="+str(page_count),cookies=jar)
html=BeautifulSoup(data.text,"lxml")
def add_students(page_count):
    data=requests.get("https://sas.schoology.com/enrollments/edit/members/course/372447719/ajax?p="+str(page_count),cookies=jar)
    html=BeautifulSoup(data.text,"lxml")
    rows=html.findAll("tr")
    temp_list={}
    for i in range(len(rows)):
        if rows[i].find("span",{"class":"admin-icon"}) is not None or rows[i].find("span",{"class":"action-links-unfold-text"}) is not None:
            pass
        else:
            temp_list[int(rows[i].find("a")['href'].split("/user/")[1])] = rows[i].find("a")['title']
    return temp_list

def add_courses(teacher_id):
    data=requests.get("https://sas.schoology.com/user/"+str(teacher_id)+"/courses/list?destination=/user/" + str(teacher_id)+"/info",cookies=jar)
    if data.ok is not True:
        print("WE HAVE AN ERROR with " + teacher_id)
    html=BeautifulSoup(data.text,"lxml")
    rows=html.findAll("li",{"class":"list-item"})
    temp_list=[]
    for i in range(len(rows)):
        course=rows[i].findAll("a")[1]
        course_name=course.text
        excluded=False
        for prefix in exclusion_prefixes:
            if prefix==course_name[0:len(prefix)]:
                excluded=True
        if "sandbox" in course_name.lower():
            excluded=True
        if excluded:
            pass
        else:
            if course_name in schoology_graph:
                schoology_graph[course_name].append(teacher_id)
            else:
                schoology_graph[course_name]=[teacher_id]

def check_if_teacher(teacher_id):
    data=requests.get("https://sas.schoology.com/user/"+str(teacher_id)+"/updates",cookies=jar)
    if data.ok is True:
       with open("teacher_list.txt","a+") as f:
           f.write(str(teacher_id)+",")
           print(teacher_id)





if __name__ == '__main__':
    p = multiprocessing.Pool(2)
    c = multiprocessing.Pool(2)
    import itertools
    student_list=c.map(add_students,range(53))
    students={}
    for student in student_list:
        students.update(student)
    for person in students.keys():
        check_if_teacher(person)
    """
    student_list=open("student_list.txt").read().replace("'","")
    student_list=student_list[1:len(student_list)-1]
    student_list=student_list.split(", ")
    course_list={}
    for i in student_list:
        add_courses(i)
    total=[]
    course_copy=course_list.copy()
    for i in course_copy.keys():
        if len(course_list[i])<5:
            del(course_list[i])
    """
        