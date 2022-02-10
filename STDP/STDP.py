synaptic_weights = [0,0,0,0]
pattern = [1,0,0,0]
threshold = 5
post_count = 0
clock = 1

def update_weights(pattern):
    for i in range(len(pattern)):
        if pattern[i]:
            synaptic_weights[i]+=1


while clock <= 20:

    print(clock)

    for i in range(len(pattern)):

        if pattern[i]:
            post_count+=1

    if post_count >= threshold:
        update_weights(pattern)
        post_count = 0

    clock+=1

print(synaptic_weights)