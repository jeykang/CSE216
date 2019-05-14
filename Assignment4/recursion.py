def sum_evens(u, acc = 0):
    if len(u) == 0:
        return acc
    if u[0] % 2 == 0:
        return sum_evens(u[1:], acc + u[0])
    return sum_evens(u[1:], acc)

def find_even_indices(u, i = 0, acc = []):
    if i >= len(u):
        return acc
    if i % 2 == 0:
        return find_even_indices(u, i + 1, acc + [u[i]])
    return find_even_indices(u, i + 1, acc)
    
def zips(s1, s2, res = ''):
    if len(s1) == 0:
        return res
    return zips(s1[1:], s2[1:], res + s1[0] + s2[0])

def find_vowels(s, res = ''):
    if len(s) == 0:
        return res
    if s[0].lower() in 'aeiou':
        return find_vowels(s[1:], res + s[0])
    return find_vowels(s[1:], res)

def find_vowel_indices(s, i = 0, res = ''):
    if i >= len(s):
        return res
    if s[i] in 'aeiou':
        return find_vowel_indices(s, i + 1, res + str(i))
    return find_vowel_indices(s, i + 1, res)

