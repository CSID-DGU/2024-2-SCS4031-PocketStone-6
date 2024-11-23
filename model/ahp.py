import numpy as np
import pandas as pd

criteria = ['기술점수', 'KPI점수', '동료평가점수', '개인성향', '기존프로젝트']

pairwise_matrix = np.array([
    [1,   3,   3,   5,   1],
    [1/3, 1,   1/3, 1/3, 1/5],
    [1/3, 3,   1,   3,   1],
    [1/5, 3,   1/3, 1,   1/3],
    [1,   5,   1,   3,   1]
])

df = pd.DataFrame(pairwise_matrix, index=criteria, columns=criteria)
print(df)

column_sum = pairwise_matrix.sum(axis=0)

normalized_matrix = pairwise_matrix / column_sum
print(pd.DataFrame(normalized_matrix, index=criteria, columns=criteria))

weights = normalized_matrix.mean(axis=1)
for crit, weight in zip(criteria, weights):
    print(f"{crit}: {weight:.4f}")

result_df = pd.DataFrame({
    '기준': criteria,
    '가중치': weights
}).sort_values(by='가중치', ascending=False)
print(result_df)
result_dict = result_df.set_index('기준')['가중치'].to_dict()
print(result_dict)