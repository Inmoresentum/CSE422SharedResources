import sys

sys.stdin = open("input.txt", 'r')

graph: [[str]] = []
while True:
    try:
        graph.append(input().split(" "))
    except EOFError:
        break

isVisited: [[bool]] = []
for _i in range(len(graph)):
    isVisited.append([])
    for second_index in range(len(graph[0])):
        isVisited[_i].append(False)

max_size: int = 0
cur_size: int = 0


def dfs(i: int, j: int):
    isVisited[i][j] = True
    global cur_size
    cur_size += 1
    # Time to check which node I can go
    if i > 0 and j > 0 and not isVisited[i - 1][j - 1] and \
            graph[i - 1][j - 1] == 'Y':
        dfs(i - 1, j - 1)
    if i > 0 and not isVisited[i - 1][j] and graph[i - 1][j] == 'Y':
        dfs(i - 1, j)
    if i > 0 and j < len(graph[0]) - 1 and not isVisited[i - 1][j + 1] and \
            graph[i - 1][j + 1] == 'Y':
        dfs(i - 1, j + 1)
    if j > 0 and not isVisited[i][j - 1] and graph[i][j - 1] == 'Y':
        dfs(i, j - 1)
    if j < len(graph[0]) - 1 and not isVisited[i][j + 1] and graph[i][j + 1] == 'Y':
        dfs(i, j + 1)
    if i < len(graph) - 1 and j > 0 and not isVisited[i + 1][j - 1] and \
            graph[i + 1][j - 1] == 'Y':
        dfs(i + 1, j - 1)
    if i < len(graph) - 1 and j < len(graph[0]) - 1 and not isVisited[i + 1][j + 1] and graph[i + 1][j + 1] == 'Y':
        dfs(i + 1, j + 1)
    if i < len(graph) - 1 and not isVisited[i + 1][j] and graph[i + 1][j] == 'Y':
        dfs(i + 1, j)


for _i in range(len(graph)):
    for second_index in range(len(graph[0])):
        if not isVisited[_i][second_index] and graph[_i][second_index] == 'Y':
            dfs(_i, second_index)
            max_size = max(cur_size, max_size)
            cur_size = 0

print(max_size)
