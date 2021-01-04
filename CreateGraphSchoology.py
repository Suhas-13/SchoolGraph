edge_list={}
import requests
from bs4 import BeautifulSoup
cookies={
    "domain": ".sas.schoology.com",
    "name": "",
    "path": "/",
    "value": ""
}
jar = requests.cookies.RequestsCookieJar()
jar.set(cookies['name'],cookies['value'],domain=cookies['domain'],path=cookies['path'])
def create_graph(course_list):
    for course in course_list.keys():
        for student in course_list[course]:
            for other_student in course_list[course]:
                if student != other_student:
                    if edge_list.get(student):
                        if edge_list[student].get(other_student):
                            edge_list[student][other_student].append(course)
                        else:
                            edge_list[student][other_student]=[course]
                    else:
                        edge_list[student]={other_student:[course]}
                    


def read_data(name):
    course_list=eval(open(name).read())
    return course_list

def get_name(student_id):
    return student_list[student_id]
    
def get_names(student_list):
    output=[]
    for student in student_list:
        student_list.append(get_name(student))
    return student_list
def find_intersection(list_of_students):

    list_of_student_sets=[]
    for student in list_of_students:
        student_set=set(edge_list[student].keys())
        list_of_student_sets.append(student_set)

    intersection=set.intersection(*list_of_student_sets)
    return(list(intersection))
#create_graph(read_data("courses_real.txt"))
edge_list=read_data("courses_real.txt")
student_list = read_data("student_ids.txt")