import random, math

synaptic_weights = [0,0,0,0,0,0,0,0,0]
target = [1,0,1,0,1,0,1,0,1]
noise = [0,0,0,0,0,0,0,0,0] #No Noise
active = [1,1,1,1,1,1,1,1,1]
threshold = 5
post_count = 0
clock = 1
fired = 0
activated_mem = [0,0,0,0,0,0,0,0,0]

pattern_1_cycles = 100
pattern_2_cycles = 500


def noise_generator():
    choice_set = [0,0,0,0,0,0,0,0,1]
    output_set = [0,0,0,0,0,0,0,0,0]
    for i in range(len(output_set)):
        output_set[i] = random.choice(choice_set)
    return output_set

def pretty_print(pattern):
    for i in range(len(pattern)):
        print("%6d" % pattern[i], end="")
        if(i%3==2):
            print()
    print("-------")


def update_weights(mem, dw, mode):
    if mode:
        for i in range(len(mem)):
            if mem[i] == 1:
                synaptic_weights[i]+=dw
    else: 
        for i in range(len(mem)):
            if mem[i] == 0:
                synaptic_weights[i]+=dw


while clock <= pattern_1_cycles:

    if fired:
        # Strong LTD
        fired = 0
        update_weights(activated_mem, -5, True)
        activated_mem = [0,0,0,0,0,0,0,0,0]


    for i in range(len(active)):

        # Update inactive neurons

        if(active[i]<1):
            active[i] += 1

    pattern = random.choice([target, target, target, target, target, target, target, target, target, noise_generator()])


    for i in range(len(pattern)):

        # PRE Fire

        if pattern[i] and (active[i] == 1):

            # Add fire to IAF POST

            activated_mem[i] = 1
            
            post_count += 1
            active[i] = -1

    if post_count >= threshold:

        # POST Fire
        fired = 1

        # LTP
        update_weights(activated_mem, 3, True)

        # Weak LTD
        update_weights(activated_mem, -1, False)
        post_count = 0
        activated_mem = [0,0,0,0,0,0,0,0,0]


    # print("Clk", clock)
    # print("Syn Wt", synaptic_weights)
    # print("Pattern", pattern)
    # print("Act Mem", activated_mem)
    # print("Fired", fired)
    # print("Active", active)
    # print("Post Count", post_count)
    # print("---")

    clock+=1

pretty_print(synaptic_weights)

clock = 0
target = [1,1,1,0,0,0,1,1,1]

while clock <= pattern_2_cycles:

    if fired:
        # Strong LTD
        fired = 0
        update_weights(activated_mem, -5, True)
        activated_mem = [0,0,0,0,0,0,0,0,0]


    for i in range(len(active)):

        # Update inactive neurons

        if(active[i]<1):
            active[i] += 1

    pattern = random.choice([target, target, target, target, target, target, target, target, target, noise_generator()])


    for i in range(len(pattern)):

        # PRE Fire

        if pattern[i] and (active[i] == 1):

            # Add fire to IAF POST

            activated_mem[i] = 1
            
            post_count += 1
            active[i] = -1

    if post_count >= threshold:

        # POST Fire
        fired = 1

        # LTP
        update_weights(activated_mem, 3, True)

        # Weak LTD
        update_weights(activated_mem, -1, False)
        post_count = 0
        activated_mem = [0,0,0,0,0,0,0,0,0]


    # print("Clk", clock)
    # print("Syn Wt", synaptic_weights)
    # print("Pattern", pattern)
    # print("Act Mem", activated_mem)
    # print("Fired", fired)
    # print("Active", active)
    # print("Post Count", post_count)
    # print("---")

    clock+=1

pretty_print(synaptic_weights)