w = [0,0,0,0,0]
tests = [
    {'x':[1,-1,-1,-1,1],'y':1},
    {'x':[-1,1,-1,-1,1],'y':-1},
    {'x':[-1,-1,1,-1,1],'y':-1},
    {'x':[-1,-1,-1,1,1],'y':-1}
]

# W(n+1) = W(n) + x.y
i = 1

for test in tests:
    x = test['x']
    y = test['y']
    delW = [xi * y for xi in x]
    w = [a + b for a, b in zip(w, delW)]
    print("Iteration",i,"-",w)
    i+=1
