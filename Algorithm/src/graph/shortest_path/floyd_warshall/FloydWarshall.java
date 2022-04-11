package graph.shortest_path.floyd_warshall;

public class FloydWarshall {

	/**
	 * 모든 쌍 최단 경로 알고리즘
	 * 모든 쌍? 각 모든 정점에서 각 모든 정점까지의 최단 거리를 모두 구한다는 의미
	 * 
	 * 다익스트라와의 차이점?
	 *  다익스트라는 양의 가중치만 허용하지만, 플로이드는 음수 사이클만 없다면 음의 가중치도 허용한다.
	 *  
	 * 시간 복잡도 : O(N^3) -> 3중for문
	 * 다익스트라는 우선순위 큐를 쓰지 않으면 O(N^2)의 시간복잡도를 보이는데,
	 *  모든 정점에 대해 최단 거리를 구하려면 O(N*N^2) = O(N^3) 이 걸린다.
	 *  (물론 다익스트라가 더 빠르지만, 플로이드는 구현이 쉽다는 이점이 있다.)
	 *  
	 *  
	 * # 초기화 # 
	 * 정점 개수 N 크기 만큼의 이차원 배열을 생성하고,
	 * 행 인덱스 -> 출발 정점 
	 * 열 인덱스 -> 도착 정점
	 *  이 되어, 연결된 경우는 가중치를 표시하고,
	 *         연결되지 않은 경우는 INF 값을 저장해둔다.
	 * (주의 : 알고리즘 수행과정에서 INF에 덧셈을 하는 경우가 생길 수 있으니, INF는 충분히 크지만 MAX_VALUE에서 떨어진 값으로 설정한다.)
	 * 
	 * 
	 * # 수행 # 
	 * 경유지
	 *  출발지
	 *   도착지 를 인덱스로 3중 for문을 수행한다.
	 * 
	 *   
	 * # 결과 #
	 * 2차원 배열에는 각 정점에서 다른 정점으로 가는 최소비용이 저장되어있다.
	 * 
	 */
	
	public static void main(String[] args) {
		
		// 아래 그래프의 각 정점의 다른 모든 정점으로의 최단 거리를 모두 구하기
		// 
		// (0) ---3--- (2) ---1--- (4)
		//   \        /    \
		//    8      4      2
		//     \    /        \
		//       (1) ---10---(3)
		//
		
		// 0은 연결되지 않았음을 의미
		int data[][] = {
		   //    0  1  2  3  4
		/*0*/	{0, 8, 3, 0, 0},
		/*1*/	{8, 0, 4,10, 0},
		/*2*/	{3, 4, 0, 2, 1},
		/*3*/	{0,10, 2, 0, 0},
		/*4*/	{0, 0, 1, 0, 0}
				};
		
		int N = 5; // 정점 수
		
		// 0. 최단거리를 저장할 배열 생성
		int dp[][] = new int[N][N];
		
		// 1. 연결된 부분은 가중치를, 연결되지 않은 부분은 INF값을 저장
		int INF = Integer.MAX_VALUE/2;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(data[i][j]!=0) {
					dp[i][j] = data[i][j];
				} else {
					dp[i][j] = INF;
				}
			}
		}
		
		// 3. 경유지, 출발지, 도착지 순으로 3중 for문을 수행하여 최단 거리를 구해나간다. (경출도 -> 경찰과 도둑)
		for(int k=0; k<N; k++) {
			for(int i=0; i<N; i++) {
				if(k==i) continue;
				for(int j=0; j<N; j++) {
					if(j==k || j==i) continue;
					// i부터 j까지의 최단 거리는, i부터 경유지 k까지 최단거리 + k부터 도착지 j까지 최단거리와 비교해서 더 작은 쪽으로 갱신된다. 
					dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);  
					/**
					 * 가중치가 음수인 경우에 INF보다 작은 값이 저장되므로, 
					 *  INF보다 작아지지 않도록 처리를 해주는 것이 좋다.
					 */
				}
			}
		}
		
		// 4. 결과 확인
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.print((dp[i][j]==INF?"∞":dp[i][j])+" ");
			}
			System.out.println();
		}
		
		System.out.println(
				"		 (0) ---3--- (2) ---1--- (4)\r\n" + 
				"		   \\        /    \\\r\n" + 
				"		    8      4      2\r\n" + 
				"		     \\    /        \\\r\n" + 
				"		       (1) ---10---(3)\r\n" 
				);
	}
}
