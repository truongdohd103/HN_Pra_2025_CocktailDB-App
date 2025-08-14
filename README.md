# HN_Pra_2025_CocktailDB-App

## Kiến trúc MVP (Model-View-Presenter)

Dự án này sử dụng kiến trúc MVP với Android thuần, không sử dụng coroutines.

### Cấu trúc thư mục

```
app/src/main/java/com/sun/cocktaildb/
├── data/
│   ├── model/          # Data models (Category, Cocktail)
│   └── repository/     # Repository + impl
├── screen/
│   ├── base/           # BaseActivity, BasePresenter, BaseView
│   ├── home/           # HomePresenter, HomeView, adapters
│   ├── homescreen/     # HomeScreenActivity
│   └── splash/         # SplashPresenter, SplashView
└── utils/              # Utility classes
```

### MVP Pattern

HomeScreen sử dụng MVP pattern:

- **Model**: Data models và Repository
- **View**: HomeScreenActivity implement HomeView interface
- **Presenter**: HomePresenter xử lý business logic

### Các tính năng chính

- ✅ Home screen với categories và popular cocktails
- ✅ Bottom navigation
- ✅ Clean architecture với MVP
- ✅ Executor và Handler cho async operations
- ✅ ViewBinding cho UI interactions

### Cách sử dụng

1. Build và run project
2. App sẽ khởi động với HomeScreen
3. Sử dụng bottom navigation
4. Xem categories và popular cocktails