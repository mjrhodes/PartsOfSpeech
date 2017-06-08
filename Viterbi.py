import json
from pprint import pprint


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
    print_stuff(obs, states, start_p, trans_p, emit_p)


def viterbi(obs, states, start_p, trans_p, emit_p):
    V = [{}]
    for st in states:
        V[0][st] = {"prob": start_p[st] * emit_p[st][obs[0]], "prev": None}
    # Run Viterbi when t > 0
    for t in range(1, len(obs)):
        V.append({})
        for st in states:
            max_tr_prob = max(V[t - 1][prev_st]["prob"] * trans_p[prev_st][st] for prev_st in states)
            for prev_st in states:
                if V[t - 1][prev_st]["prob"] * trans_p[prev_st][st] == max_tr_prob:
                    max_prob = max_tr_prob * emit_p[st][obs[t]]
                    V[t][st] = {"prob": max_prob, "prev": prev_st}
                    break
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


def print_stuff(obs, states, start_p, trans_p, emit_p):
    pprint(obs)
    pprint(states)
    pprint(start_p)
    pprint(trans_p)
    pprint(emit_p)

main()