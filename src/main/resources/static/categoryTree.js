// 가상의 카테고리 데이터
const categories = [
    { id: 1, name: "전체 카테고리", parentId: null },
    { id: 2, name: "전자제품", parentId: 1 },
    { id: 3, name: "의류", parentId: 1 },
    { id: 4, name: "스마트폰", parentId: 2 },
    { id: 5, name: "노트북", parentId: 2 },
    { id: 6, name: "남성 의류", parentId: 3 },
    { id: 7, name: "여성 의류", parentId: 3 },
];

// 트리 구조로 카테고리 정보를 구성하는 함수
function buildCategoryTree(categories, parentId = null) {
    return categories
        .filter(category => category.parentId === parentId)
        .map(category => ({
            ...category,
            children: buildCategoryTree(categories, category.id)
        }));
}

// 트리 구조의 카테고리를 HTML 요소로 변환하는 함수
function createCategoryElement(category) {
    const element = document.createElement('div');
    element.textContent = category.name;

    if (category.children.length > 0) {
        const list = document.createElement('ul');
        category.children.forEach(child => {
            const item = document.createElement('li');
            item.appendChild(createCategoryElement(child));
            list.appendChild(item);
        });
        element.appendChild(list);
    }

    return element;
}

// 카테고리 트리를 화면에 표시하는 함수
function displayCategoryTree() {
    const categoryTree = buildCategoryTree(categories);
    const rootElement = document.getElementById('categoryTree');

    categoryTree.forEach(category => {
        rootElement.appendChild(createCategoryElement(category));
    });
}

// 페이지 로드 시 카테고리 트리 표시
document.addEventListener('DOMContentLoaded', displayCategoryTree);
