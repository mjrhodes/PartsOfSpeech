import json


def main():
    with open('obs.json') as obs_data:
        obs = json.load(obs_data)
    with open('states.json') as states_data:
        states = json.load(states_data)
    with open('start_p.json') as start_p_data:
        start_p = json.load(start_p_data)
    with open('trans_p.json') as trans_p_data:
        trans_p = json.load(trans_p_data)
    with open('emit_p.json') as emit_p_data:
        emit_p = json.load(emit_p_data)
    viterbi(obs, states, start_p, trans_p, emit_p)


def viterbi(obs, states, start_p, trans_p, emit_p):
    V = [{}]
    for st in states:
        ########################### tweaked
        if emit_p[st].has_key(obs[0]):
            V[0][st] = {"prob": start_p[st] * emit_p[st][obs[0]], "prev": None}
        else:
            V[0][st] = {"prob": start_p[st] * subZeroProbForWord(obs), "prev": None}
        ###########################
    # Run Viterbi when t > 0
    for t in range(1, len(obs)):
        V.append({})
        for st in states:

            ########################### tweaked
            max_tr_prob = 0
            for prev_st in states:
                if trans_p[prev_st].has_key(st):
                    value = V[t - 1][prev_st]["prob"] * trans_p[prev_st][st]
                else:
                    value = V[t - 1][prev_st]["prob"] * subZeroProbForState(states)
                if (value > max_tr_prob):
                    max_tr_prob = value
            ###########################

            for prev_st in states:

                ########################### tweaked
                if trans_p[prev_st].has_key(st):
                    value = V[t - 1][prev_st]["prob"] * trans_p[prev_st][st]
                else:
                    value = V[t - 1][prev_st]["prob"] * subZeroProbForState(states)
                if value == max_tr_prob:
                    if emit_p[st].has_key(obs[t]):
                        max_prob = max_tr_prob * emit_p[st][obs[t]]
                    else:
                        max_prob = max_tr_prob * subZeroProbForWord(obs)
                    V[t][st] = {"prob": max_prob, "prev": prev_st}
                    break
                ###########################

    for line in dptable(V):
        print line
    opt = []
    # The highest probability
    max_prob = max(value["prob"] for value in V[-1].values())
    previous = None
    # Get most probable state and its backtrack
    for st, data in V[-1].items():
        if data["prob"] == max_prob:
            opt.append(st)
            previous = st
            break
    # Follow the backtrack till the first observation
    for t in range(len(V) - 2, -1, -1):
        opt.insert(0, V[t + 1][previous]["prev"])
        previous = V[t + 1][previous]["prev"]

    print 'The steps of states are ' + ' '.join(opt) + ' with highest probability of %s' % max_prob


def dptable(V):
    # Print a table of steps from dictionary
    yield " ".join(("%12d" % i) for i in range(len(V)))
    for state in V[0]:
        yield "%.7s: " % state + " ".join("%.7s" % ("%f" % v[state]["prob"]) for v in V)


def subZeroProbForWord(obs):
    return 1.0/(len(obs)**2)


def subZeroProbForState(states):
    return 1.0/(len(states)**2)


main()
