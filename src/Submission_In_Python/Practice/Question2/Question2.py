import collections

rows: int = int(input())
columns: int = int(input())
graph: [[str]] = []
cost: [[int]] = []

for i in range(rows):
    graph.append(input().split(" "))
    cost.append([int(1e9)] * columns)


def bfs(first_index: int, second_index: int):
    is_visited: [[bool]] = []
    for _i in range(rows):
        is_visited.append([False for _j in range(columns)])
    cur_cost: int = 1
    queue = collections.deque()
    queue.append((first_index, second_index))
    is_visited[first_index][second_index] = True
    # Time to check
    while len(queue) != 0:
        found_human: bool = False
        first: int = queue[0][0]
        second: int = queue[0][1]
        # where can I go now
        if first > 0 and not is_visited[first - 1][second] and graph[first - 1][second] == 'H':
            found_human = True
            queue.append((first - 1, second))
            cost[first - 1][second] = min(cost[first - 1][second], cur_cost)
            is_visited[first - 1][second] = True
        if first < rows - 1 and not is_visited[first + 1][second] and graph[first + 1][second] == 'H':
            found_human = True
            queue.append((first + 1, second))
            cost[first + 1][second] = min(cost[first + 1][second], cur_cost)
            is_visited[first + 1][second] = True
        if second > 0 and not is_visited[first][second - 1] and graph[first][second - 1] == 'H':
            found_human = True
            queue.append((first, second - 1))
            cost[first][second - 1] = min(cost[first][second - 1], cur_cost)
            is_visited[first][second - 1] = True
        if second < columns - 1 and not is_visited[first][second + 1] and graph[first][second + 1] == 'H':
            found_human = True
            queue.append((first, second + 1))
            cost[first][second + 1] = min(cost[first][second + 1], cur_cost)
            is_visited[first][second + 1] = True
        if found_human:
            cur_cost += 1
        queue.popleft()


for i in range(rows):
    for j in range(columns):
        if graph[i][j] == 'A':
            bfs(i, j)

min_time: int = int(-1e9)
number_of_survived_humans = 0
for i in range(rows):
    for j in range(columns):
        if graph[i][j] == 'H' and cost[i][j] != int(1e9):
            min_time = max(min_time, cost[i][j])
        if graph[i][j] == 'H' and cost[i][j] == int(1e9):
            number_of_survived_humans += 1

print(f"Time: {min_time} minutes")
print(f"No one survived" if number_of_survived_humans == 0 else f"{number_of_survived_humans} survived")
