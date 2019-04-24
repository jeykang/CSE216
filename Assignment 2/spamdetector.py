# -*- coding: utf-8 -*-
"""
Created on Thu Apr  4 15:36:41 2019

@author: techj
"""
import string
from queueimpl import PriorityQueue
import sys
    
def pspam(message):
    pqueue = PriorityQueue(15)
    pbad = load_probabilities("bad.txt")
    pgood = load_probabilities("good.txt")
    msg_file = open(message, "r")
    stripped_lines = [line.split() for line in msg_file.readlines()]
    for line in stripped_lines:
        for word in line:
            prcs_wrd = word.strip(string.punctuation).lower()
            spam_wrd = spamicity(prcs_wrd, pbad, pgood)
            if(spam_wrd != None):
                pqueue.insert(prcs_wrd, spam_wrd)
    msg_file.close()
    return combined_probability(pqueue.getSpamicityList())

def load_probabilities(path):
    loaded_file = open(path, "r")
    lines_lists = [line.split() for line in loaded_file.readlines()]
    final_dict = {}
    for line in lines_lists:
        final_dict[line[1]] = float(line[0])
    loaded_file.close()
    return final_dict

def spamicity(w, pbad, pgood):
    if w in pbad and w in pgood:
        return pbad[w] / (pbad[w] + pgood[w])
    else:
        return None 
    
def combined_probability(spamicitylist):
    p = q = 1.0
    for x in spamicitylist: 
        p *= x
        q *= (1.0 - x)
    return p / (p + q) 

spamprob = pspam(sys.argv[1])
if(spamprob > 0.7):
    print("Result:", spamprob, "(high probability of spam)\n")
elif(spamprob > 0.5):
    print("Result:", spamprob, "(probably a spam)\n")
elif(spamprob > 0.3):
    print("Result:", spamprob, "(probably not a spam)\n")
else:
    print("Result:", spamprob, "(high probability of not a spam)\n")
    
print("File contents:\n")
print(open(sys.argv[1], "r").read())
