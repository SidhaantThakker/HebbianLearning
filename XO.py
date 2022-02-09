import random

w = [0,0,0,
     0,0,0,
     0,0,0]
target_set = [{'pattern':[1,-1,1,-1,1,-1,1,-1,1], 'value': -1},
         {'pattern':[1,1,1,1,-1,1,1,1,1], 'value': 1}]

trials = 20
trial_type_set = [0,1]

# W(n+1) = W(n) + x.y

def print_grid(w):
    for i in range(len(w)):
        print("%4d" % w[i], end="")
        if(i%3==2):
            print()
    print("-------")

for i in range(trials):
    target = random.choice(target_set)
    pattern = target['pattern']
    value = target['value']
    delW = [pattern_bit * value for pattern_bit in pattern]
    w = [i + del_i for i, del_i in zip(w, delW)]
    print_grid(w)




