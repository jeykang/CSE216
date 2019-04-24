import sys
class Person(object):
    def __init__(self, name, idnumber):
        self.name = name
        self.idnumber = idnumber

class SportsPerson:
    def __init__(self, isathelete):
        self.isathlete = isathelete
    
class Athlete(Person, SportsPerson):
    def __init__(self, name, idnumber, isathlete):
        Person.__init__(self, name, idnumber)
        SportsPerson.__init__(self, isathlete)
        
    def display(self):
        print(self.name, self.idnumber, self.isathlete)

      
Athlete(sys.argv[1], sys.argv[2], sys.argv[3]).display()