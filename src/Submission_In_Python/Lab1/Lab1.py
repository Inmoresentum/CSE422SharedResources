import sys
from queue import PriorityQueue

'''
    Please use Python 3.10.8 or higher
'''


class Node:
    __slots__ = ("heuristic_value", "adj_nodes")

    def __init__(self) -> None:
        self.heuristic_value: int = int(1e9)
        self.adj_nodes: list[tuple[str, int]] = []

    def __str__(self) -> str:
        return f"""heuristic_value = {self.heuristic_value} adj_nodes = " {self.adj_nodes}"""


graph: dict[str, Node] = {}
distance: dict[str, int] = {}
parent: dict[str, str] = {}
input_lines: list[str] = []


def take_input_from_txt_file() -> None:
    with open("input.txt", "r") as file:
        for readline in file:
            input_lines.append(readline.strip())
            # Time to initialize the graph
            city_name: str = readline.strip().split()[0]
            graph[city_name] = Node()
            distance[city_name] = int(1e9)
            parent[city_name] = "null"
        file.close()  # Closing the input stream


take_input_from_txt_file()


# Time to build the graph
def build_graph() -> None:
    for _i in input_lines:
        cur_line = _i.split()
        city_name = cur_line[0]
        heuristic_value = int(cur_line[1])
        graph.get(city_name).heuristic_value = heuristic_value
        for _j in range(2, len(cur_line), 2):
            graph.get(city_name).adj_nodes.append((cur_line[_j], int(cur_line[_j + 1])))


build_graph()


# Time to do a star search
def a_start_search(source: str, destination: str) -> None:
    distance[source] = 0
    final_cost = graph.get(source).heuristic_value
    pq: PriorityQueue[tuple[int, str, int]] = PriorityQueue()
    # putting in tuple in this format -> final_cost, city_name, distance/cost
    pq.put((final_cost, source, 0))
    while not pq.empty():
        cur_final_cost, cur_visiting_node, cur_distance = pq.get()
        if cur_visiting_node == destination:
            break
        if distance.get(cur_visiting_node) != cur_distance:
            continue
        for k in graph.get(cur_visiting_node).adj_nodes:
            if distance.get(k[0]) > cur_distance + k[1]:
                distance[k[0]] = cur_distance + k[1]
                parent[k[0]] = cur_visiting_node
                update_final_cost = distance.get(k[0]) + graph.get(k[0]).heuristic_value
                pq.put((update_final_cost, k[0], distance.get(k[0])))


starting_node: str = input("Start node: ")
ending_node: str = input("Destination: ")
a_start_search(starting_node, ending_node)

# If no path found then simply print "NO PATH FOUND" and then exit
if distance.get(ending_node) == int(1e9):
    print("NO PATH FOUND")
    sys.exit(0)
print(distance.get(ending_node))
path: list[str] = []
cur_node = ending_node
while cur_node != starting_node:
    path.append(cur_node)
    cur_node = parent.get(cur_node)
path.append(starting_node)
path.reverse()
for i in range(len(path) - 1):
    print(path[i] + " -> ", end="")
print(path[len(path) - 1])
